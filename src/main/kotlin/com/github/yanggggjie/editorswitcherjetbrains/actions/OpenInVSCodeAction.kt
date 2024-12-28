package com.github.yanggggjie.editorswitcherjetbrains.actions

import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import java.awt.Desktop
import java.net.URI

class OpenInVSCodeAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        val editor = e.getData(CommonDataKeys.EDITOR)
        val virtualFile = e.getData(CommonDataKeys.VIRTUAL_FILE)

        val vscodeUrl = if (editor != null && virtualFile != null) {
            // 有打开的文件，打开具体文件和位置
            val caretModel = editor.caretModel
            val line = caretModel.logicalPosition.line + 1
            val column = caretModel.logicalPosition.column + 1
            val projectPath = project.basePath
            val filePath = virtualFile.path.removePrefix(projectPath ?: "")
            "cursor://file$projectPath$filePath:$line:$column"
        } else {
            // 没有打开的文件，打开项目根目录
            val projectPath = project.basePath
            "cursor://file$projectPath"
        }

        try {
            Desktop.getDesktop().browse(URI(vscodeUrl))
        } catch (ex: Exception) {
            // 处理错误
        }
    }

    override fun update(e: AnActionEvent) {
        // 只要有项目就启用按钮
        val project = e.project
        e.presentation.isEnabledAndVisible = project != null
    }

    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.BGT
    }
}
