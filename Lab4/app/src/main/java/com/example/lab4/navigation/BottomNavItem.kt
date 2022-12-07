package com.example.lab4.navigation

import com.example.lab4.R

sealed class NavItem(var title:String, var icon:Int, var path:String){
    object Keypad: NavItem("KeyPad", R.drawable.ic_keypad,"keypad")
    object Recent: NavItem("Recents", R.drawable.ic_recents,"recents")
    object Contacts: NavItem("Contacts", R.drawable.ic_contact,"contacts")
}