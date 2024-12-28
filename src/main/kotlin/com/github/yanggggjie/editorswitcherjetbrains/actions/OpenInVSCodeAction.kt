package com.github.yanggggjie.editorswitcherjetbrains.actions

import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.fileEditor.FileEditorManager
import java.awt.Desktop
import java.net.URI

class OpenInVSCodeAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        val editor = e.getData(CommonDataKeys.EDITOR) ?: return
        val virtualFile = e.getData(CommonDataKeys.VIRTUAL_FILE) ?: return

        // 获取当前光标位置
        val caretModel = editor.caretModel
        val line = caretModel.logicalPosition.line + 1 // VSCode行号从1开始
        val column = caretModel.logicalPosition.column + 1

        // 构建VSCode URL
        val projectPath = project.basePath
        val filePath = virtualFile.path.removePrefix(projectPath ?: "")
        val vscodeUrl = "cursor://file$projectPath$filePath:$line:$column"

        try {
            Desktop.getDesktop().browse(URI(vscodeUrl))
        } catch (ex: Exception) {
            // 处理错误
        }
    }

    override fun update(e: AnActionEvent) {
        // 只在编辑器中有文件打开时启用该action
        val virtualFile = e.getData(CommonDataKeys.VIRTUAL_FILE)
        e.presentation.isEnabledAndVisible = virtualFile != null
    }

    // 添加这个方法来指定更新线程
    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.BGT
    }
}
