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

package com.zxy.ijplugin.miniprogram.inspections

import com.intellij.codeInspection.LocalInspectionTool

abstract class WechatMiniProgramInspectionBase : LocalInspectionTool() {

    override fun getGroupDisplayName(): String {
        return "Wechat mini program"
    }

}

abstract class WXMLInspectionBase : WechatMiniProgramInspectionBase()