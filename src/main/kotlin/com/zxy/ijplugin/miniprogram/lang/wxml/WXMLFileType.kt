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

package com.zxy.ijplugin.miniprogram.lang.wxml

import com.intellij.ide.highlighter.XmlLikeFileType
import com.zxy.ijplugin.miniprogram.icons.WechatMiniProgramIcons
import javax.swing.Icon

open class WXMLFileType : XmlLikeFileType(WXMLLanguage.INSTANCE) {

    companion object {
        @JvmField
        val INSTANCE = WXMLFileType()
    }

    override fun getIcon(): Icon? {
        return WechatMiniProgramIcons.WXML
    }

    override fun getName(): String {
        return "WXML"
    }

    override fun getDefaultExtension(): String {
        return "wxml"
    }

    override fun getDescription(): String {
        return "WeiXin Markup Language"
    }

}