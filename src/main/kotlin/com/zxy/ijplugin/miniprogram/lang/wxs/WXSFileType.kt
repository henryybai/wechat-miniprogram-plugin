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

package com.zxy.ijplugin.miniprogram.lang.wxs

import com.intellij.lang.javascript.JavaScriptSupportLoader
import com.intellij.openapi.fileTypes.LanguageFileType
import com.zxy.ijplugin.miniprogram.icons.WechatMiniProgramIcons
import javax.swing.Icon

open class WXSFileType : LanguageFileType(JavaScriptSupportLoader.JAVASCRIPT_1_8) {
    companion object {
        @JvmField
        val INSTANCE = WXSFileType()
    }

    override fun getIcon(): Icon? {
        return WechatMiniProgramIcons.WXS
    }

    override fun getName(): String {
        return "WXS"
    }

    override fun getDefaultExtension(): String {
        return "wxs"
    }

    override fun getDescription(): String {
        return "Script of wechat miniprogram"
    }

    override fun getDisplayName(): String {
        return "Weixin Script"
    }
}