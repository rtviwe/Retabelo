package rtviwe.com.retabelo.main

import androidx.fragment.app.Fragment

abstract class MainBaseFragment : Fragment() {

    abstract val layoutId: Int

    abstract fun scrollToTop()
}