package rtviwe.com.retabelo.main.fragments.recommendations

import rtviwe.com.retabelo.R
import rtviwe.com.retabelo.main.fragments.BaseFragment


class RecommendationsFragment : BaseFragment() {

    override val layoutId = R.layout.recommendations_fragment

    private lateinit var recommendationsAdapter: RecommendationsAdapter
    private lateinit var viewModel: RecommendationsViewModel

}