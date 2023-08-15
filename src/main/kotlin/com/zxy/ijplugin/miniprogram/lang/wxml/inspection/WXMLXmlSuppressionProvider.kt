/*
 *     Copyright (c) [2019] [zxy]
 *     [wechat-miniprogram-plugin] is licensed under the Mulan PSL v1.
 *     You can use this software according to the terms and conditions of the Mulan PSL v1.
 *     You may obtain a copy of Mulan PSL v1 at:
 *         http://license.coscl.org.cn/MulanPSL
 *     THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND, EITHER EXPRESS OR
 *     IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT, MERCHANTABILITY OR FIT FOR A PARTICULAR
 *     PURPOSE.
 *     See the Mulan PSL v1 for more details.
 */

package com.zxy.ijplugin.miniprogram.lang.wxml.inspection

import com.intellij.codeInspection.XmlSuppressionProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.zxy.ijplugin.miniprogram.lang.wxml.WXMLPsiFile

class WXMLXmlSuppressionProvider : XmlSuppressionProvider() {
    override fun suppressForTag(element: PsiElement, inspectionId: String) {
    }

    override fun suppressForFile(element: PsiElement, inspectionId: String) {
    }

    override fun isSuppressedFor(element: PsiElement, inspectionId: String): Boolean {
        return inspectionId == "CheckTagEmptyBody"
    }

    override fun isProviderAvailable(file: PsiFile): Boolean {
        return file is WXMLPsiFile
    }
}