package com.zxy.ijplugin.wechat_miniprogram.reference

import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReferenceBase
import com.zxy.ijplugin.wechat_miniprogram.lang.wxml.WXMLPsiFile
import com.zxy.ijplugin.wechat_miniprogram.lang.wxml.psi.WXMLStringText
import com.zxy.ijplugin.wechat_miniprogram.lang.wxml.utils.WXMLModuleUtils
import com.zxy.ijplugin.wechat_miniprogram.utils.contentRange

class WXMLTemplateIsAttributeReference(element: WXMLStringText) :
        PsiReferenceBase<WXMLStringText>(element, element.contentRange()) {

    override fun resolve(): PsiElement? {
        val wxmlPsiFile = this.element.containingFile
        return WXMLModuleUtils.findTemplateDefinition(wxmlPsiFile as WXMLPsiFile, this.element.text)
    }

    override fun getVariants(): Array<Any> {
        return WXMLModuleUtils.findTemplateDefinitionsWithImports(element.containingFile as WXMLPsiFile).toTypedArray()
    }

    override fun isSoft(): Boolean {
        return false
    }

}