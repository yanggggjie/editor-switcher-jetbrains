package com.github.yanggggjie.editorswitcherjetbrains.listeners

import com.intellij.openapi.application.ApplicationActivationListener
import com.intellij.openapi.wm.IdeFrame

internal class MyApplicationActivationListener : ApplicationActivationListener {

    override fun applicationActivated(ideFrame: IdeFrame) {
        // 监听 IDE 窗口激活事件
        // 目前只是输出一条警告日志
    }
}
