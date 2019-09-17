package com.hexg.guideview

//success = if all permissions were granted, permissions[] list of granted permission
typealias GrantedPermissionCallback = (success: Boolean, permissions: Array<String>) -> Unit
