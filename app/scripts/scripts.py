import time
import requests
from bs4 import BeautifulSoup

import firebase_admin
from firebase_admin import credentials
from firebase_admin import firestore


BASE_URL = "https://www.say7.info/cook"
LINKS_FILE = "links.txt"
CONTENT_FILE = "content.txt"


def download_html(url) -> BeautifulSoup:
    html = requests.get(url)
    html_text = html.text
    soup = BeautifulSoup(html_text, 'lxml')
    return soup


# get links
def script1():
    url = f"{BASE_URL}/linkz_start-1.html"
    for i in range(1, 50):
        soup = download_html(url)
        div = soup.find('div', class_='lst')
        refs = div.find_all('a')

        links = []
        for ref in refs:
            links.append(f"https://{ref.get('href').replace('//', '')}")

        for link in links:
            file = open(LINKS_FILE, 'a', encoding='utf-8')
            file.write(link)
            file.write('\n')
            file.close()

        url = f"{BASE_URL}/linkz_start-{i * 20}.html"
        time.sleep(1)


# get content
def script2():
    lines = open(LINKS_FILE).readlines()
    for line in lines:
        url = line.strip()
        soup = download_html(url)
        content = ""

        main_div = soup.find('div', class_='article h-recipe')
        title = main_div.find('h1').text
        body = main_div.contents
        for index, item in enumerate(body):
            str_item = str(item)

            if str_item.startswith('<div id="sign">'):
                break

            if str_item.startswith('<h1 itemprop="name">') or (index == 0) or str_item == '\n':
                continue

            content += fix_line(str_item)

        file = open(CONTENT_FILE, 'a', encoding='utf-8')
        file.write(title)
        file.write('\n')
        file.write(content)
        file.write('\n')
        file.write('--end--\n')
        file.close()

        time.sleep(1)


# fixing line
def fix_line(line) -> str:
    line = line.replace('src="//', 'src="https://').replace('h6', 'p').replace('h5', 'p').replace('h4', 'p')\
        .replace('h3', 'p').replace('h2', 'p').replace('h1', 'p').replace('<a', '<p').replace('</a', '</p')
    return line


# add data to firestore
def script3():
    cred = credentials.Certificate('retabelo-a2065-firebase-adminsdk-qu4g4-bcbe24f1c7.json')
    firebase_admin.initialize_app(cred)
    db = firestore.client()

    name = ""
    body = ""
    prev_line_is_end = True

    lines = open(CONTENT_FILE, 'r', encoding='utf-8').readlines()
    i = 0
    for line in lines:
        if prev_line_is_end:
            name = line
            prev_line_is_end = False

        body += line

        if line == '--end--\n':
            i += 1
            recipes_ref = db.collection(u'recipes').document(str(i))

            recipes_ref.set({
                u'name': name,
                u'body': body
            })

            head = ""
            body = ""
            prev_line_is_end = True


# delete firestore
def script4():
    cred = credentials.Certificate('retabelo-a2065-firebase-adminsdk-qu4g4-bcbe24f1c7.json')
    firebase_admin.initialize_app(cred)
    db = firestore.client()
    collection_ref = db.collection(u'recipes')
    delete_collection(collection_ref, 1)


def delete_collection(coll_ref, batch_size):
    docs = coll_ref.limit(10).get()
    deleted = 0

    for doc in docs:
        doc.reference.delete()
        deleted += 1

    if deleted >= batch_size:
        return delete_collection(coll_ref, batch_size)


if __name__ == '__main__':
    script1()
    script2()
    script3()
    # script4()
    pass
