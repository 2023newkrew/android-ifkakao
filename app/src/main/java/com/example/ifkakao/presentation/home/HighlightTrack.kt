package com.example.ifkakao.presentation.home

import androidx.annotation.DrawableRes
import com.example.ifkakao.R

enum class HighlightTrack(
    val alias: String,
    @DrawableRes
    val resId: Int
) {
    Ai("AI", R.drawable.icon_ai),
    BackEnd("Backend", R.drawable.icon_be),
    Cloud("Cloud", R.drawable.icon_cd),
    DevOps("DevOps", R.drawable.icon_do),
    BlockChain("Blockchain", R.drawable.icon_bc),
    Data("Data", R.drawable.icon_dt),
    FrontEnd("Frontend", R.drawable.icon_fe),
    Mobile("Mobile", R.drawable.icon_m)
}