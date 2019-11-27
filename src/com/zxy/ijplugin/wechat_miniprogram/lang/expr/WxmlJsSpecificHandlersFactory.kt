/*
 *    Copyright (c) [2019] [zxy]
 *    [wechat-miniprogram-plugin] is licensed under the Mulan PSL v1.
 *    You can use this software according to the terms and conditions of the Mulan PSL v1.
 *    You may obtain a copy of Mulan PSL v1 at:
 *       http://license.coscl.org.cn/MulanPSL
 *    THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND, EITHER EXPRESS OR
 *    IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT, MERCHANTABILITY OR FIT FOR A PARTICULAR
 *    PURPOSE.
 *    See the Mulan PSL v1 for more details.
 *
 *
 *                      Mulan Permissive Software License，Version 1
 *
 *    Mulan Permissive Software License，Version 1 (Mulan PSL v1)
 *    August 2019 http://license.coscl.org.cn/MulanPSL
 *
 *    Your reproduction, use, modification and distribution of the Software shall be subject to Mulan PSL v1 (this License) with following terms and conditions:
 *
 *    0. Definition
 *
 *       Software means the program and related documents which are comprised of those Contribution and licensed under this License.
 *
 *       Contributor means the Individual or Legal Entity who licenses its copyrightable work under this License.
 *
 *       Legal Entity means the entity making a Contribution and all its Affiliates.
 *
 *       Affiliates means entities that control, or are controlled by, or are under common control with a party to this License, ‘control’ means direct or indirect ownership of at least fifty percent (50%) of the voting power, capital or other securities of controlled or commonly controlled entity.
 *
 *    Contribution means the copyrightable work licensed by a particular Contributor under this License.
 *
 *    1. Grant of Copyright License
 *
 *       Subject to the terms and conditions of this License, each Contributor hereby grants to you a perpetual, worldwide, royalty-free, non-exclusive, irrevocable copyright license to reproduce, use, modify, or distribute its Contribution, with modification or not.
 *
 *    2. Grant of Patent License
 *
 *       Subject to the terms and conditions of this License, each Contributor hereby grants to you a perpetual, worldwide, royalty-free, non-exclusive, irrevocable (except for revocation under this Section) patent license to make, have made, use, offer for sale, sell, import or otherwise transfer its Contribution where such patent license is only limited to the patent claims owned or controlled by such Contributor now or in future which will be necessarily infringed by its Contribution alone, or by combination of the Contribution with the Software to which the Contribution was contributed, excluding of any patent claims solely be infringed by your or others’ modification or other combinations. If you or your Affiliates directly or indirectly (including through an agent, patent licensee or assignee）, institute patent litigation (including a cross claim or counterclaim in a litigation) or other patent enforcement activities against any individual or entity by alleging that the Software or any Contribution in it infringes patents, then any patent license granted to you under this License for the Software shall terminate as of the date such litigation or activity is filed or taken.
 *
 *    3. No Trademark License
 *
 *       No trademark license is granted to use the trade names, trademarks, service marks, or product names of Contributor, except as required to fulfill notice requirements in section 4.
 *
 *    4. Distribution Restriction
 *
 *       You may distribute the Software in any medium with or without modification, whether in source or executable forms, provided that you provide recipients with a copy of this License and retain copyright, patent, trademark and disclaimer statements in the Software.
 *
 *    5. Disclaimer of Warranty and Limitation of Liability
 *
 *       The Software and Contribution in it are provided without warranties of any kind, either express or implied. In no event shall any Contributor or copyright holder be liable to you for any damages, including, but not limited to any direct, or indirect, special or consequential damages arising from your use or inability to use the Software or the Contribution in it, no matter how it’s caused or based on which legal theory, even if advised of the possibility of such damages.
 *
 *    End of the Terms and Conditions
 *
 *    How to apply the Mulan Permissive Software License，Version 1 (Mulan PSL v1) to your software
 *
 *       To apply the Mulan PSL v1 to your work, for easy identification by recipients, you are suggested to complete following three steps:
 *
 *       i. Fill in the blanks in following statement, including insert your software name, the year of the first publication of your software, and your name identified as the copyright owner;
 *       ii. Create a file named “LICENSE” which contains the whole context of this License in the first directory of your software package;
 *       iii. Attach the statement to the appropriate annotated syntax at the beginning of each source file.
 *
 *    Copyright (c) [2019] [name of copyright holder]
 *    [Software Name] is licensed under the Mulan PSL v1.
 *    You can use this software according to the terms and conditions of the Mulan PSL v1.
 *    You may obtain a copy of Mulan PSL v1 at:
 *       http://license.coscl.org.cn/MulanPSL
 *    THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND, EITHER EXPRESS OR
 *    IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT, MERCHANTABILITY OR FIT FOR A PARTICULAR
 *    PURPOSE.
 *
 *    See the Mulan PSL v1 for more details.
 */

package com.zxy.ijplugin.wechat_miniprogram.lang.expr

import com.intellij.lang.injection.InjectedLanguageManager
import com.intellij.lang.javascript.JavaScriptSpecificHandlersFactory
import com.intellij.lang.javascript.psi.*
import com.intellij.lang.javascript.psi.impl.JSReferenceExpressionImpl
import com.intellij.lang.javascript.psi.resolve.JSEvaluateContext
import com.intellij.lang.javascript.psi.resolve.JSReferenceExpressionResolver
import com.intellij.lang.javascript.psi.resolve.JSTypeEvaluator
import com.intellij.lang.javascript.psi.resolve.JSTypeProcessor
import com.intellij.psi.PsiElementResolveResult
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiManager
import com.intellij.psi.ResolveResult
import com.intellij.psi.impl.source.resolve.ResolveCache
import com.intellij.psi.util.PsiTreeUtil
import com.zxy.ijplugin.wechat_miniprogram.context.MyJSPredefinedLibraryProvider.Companion.PAGE_LIFETIMES
import com.zxy.ijplugin.wechat_miniprogram.context.RelateFileType
import com.zxy.ijplugin.wechat_miniprogram.context.findRelateFile
import com.zxy.ijplugin.wechat_miniprogram.lang.wxml.WxmlJSInjector
import com.zxy.ijplugin.wechat_miniprogram.lang.wxml.psi.WXMLAttribute
import com.zxy.ijplugin.wechat_miniprogram.lang.wxml.psi.WXMLStringText
import com.zxy.ijplugin.wechat_miniprogram.lang.wxml.utils.isEventHandler

// TODO  参考Vue插件
class WxmlJsSpecificHandlersFactory : JavaScriptSpecificHandlersFactory() {

    override fun createReferenceExpressionResolver(
            referenceExpression: JSReferenceExpressionImpl, ignorePerformanceLimits: Boolean
    ): ResolveCache.PolyVariantResolver<JSReferenceExpressionImpl> {
        return WxmlJsReferenceExpressionResolver(referenceExpression, ignorePerformanceLimits)
    }

    override fun newTypeEvaluator(context: JSEvaluateContext, processor: JSTypeProcessor): JSTypeEvaluator {
        return super.newTypeEvaluator(context, processor)
    }

}

/**
 * 控制wxml 中的表达式如何引用 js 中的标识符
 */
class WxmlJsReferenceExpressionResolver(
        referenceExpression: JSReferenceExpressionImpl,
        ignorePerformanceLimits: Boolean
) :
        JSReferenceExpressionResolver(referenceExpression, ignorePerformanceLimits) {

    override fun resolve(expression: JSReferenceExpressionImpl, incompleteCode: Boolean): Array<ResolveResult> {
        if (myReferencedName == null) return ResolveResult.EMPTY_ARRAY
        val project = expression.project
        if (myQualifier == null) {
            // TODO 优先尝试解析wxs module和wx:for创建的变量
            val psiManager = PsiManager.getInstance(project)
            val injectionHost = InjectedLanguageManager.getInstance(project).getInjectionHost(
                    expression
            )!!
            val jsFile = findRelateFile(
                    injectionHost.containingFile.virtualFile, RelateFileType.JS
            )
            if (jsFile != null) {
                val jsPsiFile = psiManager.findFile(jsFile)
                if (jsPsiFile != null) {
                    if (injectionHost is WXMLStringText && PsiTreeUtil.getParentOfType(
                                    injectionHost, WXMLAttribute::class.java
                            )?.isEventHandler() == true && !WxmlJSInjector.DOUBLE_BRACE_REGEX.matches(
                                    injectionHost.text
                            )) {
                        // 事件
                        // 找到js文件中的methods
                        resolveMethods(jsPsiFile)?.let {
                            return it
                        }
                    } else {
                        resolveProperties(jsPsiFile)?.let {
                            return it
                        }
                    }
                }
            }
        }
        return super.resolve(expression, incompleteCode)
    }

    private fun findJsCallExpressionAndResolve(
            jsPsiFile: PsiFile,
            componentApiResolve: (jsCallExpression: JSCallExpression) -> Array<ResolveResult>?,
            pageApiResolve: (jsCallExpression: JSCallExpression) -> Array<ResolveResult>?
    ): Array<ResolveResult>? {
        // 找到最外层的方法调用
        val arrayOfJSExpressionStatements = PsiTreeUtil.getChildrenOfType(
                jsPsiFile, JSExpressionStatement::class.java
        ) ?: emptyArray()
        val jsCallExpressions = arrayOfJSExpressionStatements.asSequence().mapNotNull {
            PsiTreeUtil.getChildOfType(
                    it, JSCallExpression::class.java
            )
        }
        for (jsCallExpression in jsCallExpressions) {
            val jsReferenceExpression = PsiTreeUtil.getChildOfType(
                    jsCallExpression, JSReferenceExpression::class.java
            ) ?: continue
            if (jsReferenceExpression.text == "Component") {
                // ComponentApi
                componentApiResolve(jsCallExpression)?.let {
                    if (it.isNotEmpty()) return it
                }
            } else if (jsReferenceExpression.text == "Page") {
                // PageApi
                pageApiResolve(jsCallExpression)?.let {
                    if (it.isNotEmpty()) return it
                }
            }
        }
        return null
    }

    private fun resolveMethods(jsPsiFile: PsiFile): Array<ResolveResult>? {
        return this.findJsCallExpressionAndResolve(
                jsPsiFile, this::resolveMethodsForComponent, this::resolveMethodsForPage
        )
    }

    private fun resolveMethodsForComponent(
            jsCallExpression: JSCallExpression
    ): Array<ResolveResult> {
        val results = arrayListOf<ResolveResult>()

        val properties = getCallExpressionProperties(jsCallExpression)

        // 解析componentApi里的properties属性
        val methodsProperty = properties.find {
            it.name == "methods"
        }
        addObjectKeys(methodsProperty, results)
        return results.toTypedArray()
    }

    private fun resolveMethodsForPage(jsCallExpression: JSCallExpression): Array<ResolveResult>? {
        if (PAGE_LIFETIMES.contains(this.myReferencedName)) {
            return null
        }
        val properties = getCallExpressionProperties(jsCallExpression)
        val result = properties.find {
            it.name == this.myReferencedName
        }
        if (result != null) {
            return arrayOf(PsiElementResolveResult(result))
        }
        return null
    }

    private fun addObjectKeys(
            methodsProperty: JSProperty?,
            results: ArrayList<ResolveResult>
    ) {
        val methodsPropertyObjectLiteral = PsiTreeUtil.getChildOfType(
                methodsProperty, JSObjectLiteralExpression::class.java
        )
        val componentMethods = PsiTreeUtil.getChildrenOfType(methodsPropertyObjectLiteral, JSProperty::class.java)
        componentMethods?.find {
            it.name == myReferencedName
        }?.let {
            results.add(PsiElementResolveResult(it))
        }
    }

    private fun resolveProperties(jsPsiFile: PsiFile): Array<ResolveResult>? {
        return findJsCallExpressionAndResolve(
                jsPsiFile, this::resolvePropertiesForComponent, this::resolvePropertiesForPage
        )
    }

    private fun resolvePropertiesForComponent(
            jsCallExpression: JSCallExpression
    ): Array<ResolveResult> {
        val results = arrayListOf<ResolveResult>()

        val properties = getCallExpressionProperties(jsCallExpression)

        // 解析componentApi里的properties属性
        val propertiesProperty = properties.find {
            it.name == "properties"
        }
        this.addObjectKeys(propertiesProperty, results)

        results.addAll(resolvePropertiesForPage(properties))
        return results.toTypedArray()
    }

    private fun resolvePropertiesForPage(jsCallExpression: JSCallExpression): Array<ResolveResult> {
        val properties = getCallExpressionProperties(jsCallExpression)
        return resolvePropertiesForPage(properties)
    }

    private fun getCallExpressionProperties(
            jsCallExpression: JSCallExpression
    ): Array<out JSProperty> {
        val args = PsiTreeUtil.getChildOfType(jsCallExpression, JSArgumentList::class.java)
        val objectLiteral = PsiTreeUtil.getChildOfType(args, JSObjectLiteralExpression::class.java)
        return PsiTreeUtil.getChildrenOfType(objectLiteral, JSProperty::class.java) ?: emptyArray()
    }

    private fun resolvePropertiesForPage(callExpressionProperties: Array<out JSProperty>): Array<ResolveResult> {
        val results = arrayListOf<ResolveResult>()
        val dataProperty = callExpressionProperties.find {
            it.name == "data"
        }
        val dataPropertyObjectLiteral = PsiTreeUtil.getChildOfType(
                dataProperty, JSObjectLiteralExpression::class.java
        )
        val dataItems = PsiTreeUtil.getChildrenOfType(dataPropertyObjectLiteral, JSProperty::class.java)
        dataItems?.find { it.name == myReferencedName }?.let {
            results.add(PsiElementResolveResult(it))
        }
        return results.toTypedArray()
    }

}
