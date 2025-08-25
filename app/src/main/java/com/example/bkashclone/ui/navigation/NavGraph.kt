package com.example.bkashclone.ui.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    object SendMoney : Screen("send_money")
    object MobileRecharge : Screen("mobile_recharge")
    object CashOut : Screen("cash_out")
    object Payment : Screen("payment")
    object AddMoney : Screen("add_money")
    object ScanQR : Screen("scan_qr")
    object Search : Screen("search")
    object Inbox : Screen("inbox")
}
