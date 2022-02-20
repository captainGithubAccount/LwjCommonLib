package com.lwj.common.ui.controll.tools.utils

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.ContactsContract
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.lwj.common.ui.controll.tools.ktx.toTrim
import java.lang.Exception

object SystemServiceUtil {

    /**
     * 调用系统软键盘来隐藏软键盘
     * view  触发软键盘弹出的控件
     */
    fun hideKeyboard(view: View) {
        val imm = view.context.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.hideSoftInputFromWindow(view.windowToken, 0)
    }

    /**
     * 调用系统粘贴版实现文本复制
     * */
    fun copyTextToClipboard( textSource: String, activity: Context) { //获取ClipboardManager对象
        val clipboard: ClipboardManager = activity.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager //把文本封装到ClipData中
        val clip = ClipData.newPlainText(null, textSource.toTrim()) // Set the clipboard's primary clip.
        clipboard.setPrimaryClip(clip)
    }

    /**
     * 拨打电话（跳转到拨号界面，用户手动点击拨打）
     * @param phoneNum 电话号码
     */
    fun callPhone(phoneNum: String, activity: Context) {
        try {
            val intent = Intent(Intent.ACTION_DIAL)
            val data = Uri.parse("tel:$phoneNum")
            intent.data = data
            activity.startActivity(intent)
        } catch(e: Exception) {
            e.printStackTrace()
        }
    }


    /**
     * 调用系统邮件软件   发送邮件
     * @param addresses 邮箱集合
     * @param subjectContent 短信主题
     */
    fun composeEmail(address: String, subjectContent: String, activity: Context) {

        try { //注意: ACTION_SENDTO可以在模拟器或真机上正常使用, ACTION_SEND只有在真机上才可以正常使用, 模拟器上提示 没有应用程序可以执行此操作
            val intent = Intent(Intent.ACTION_SENDTO)

            intent.setData(Uri.parse("mailto:")) //收件人集合如: String[] address = {"1@abc.com", "2@abc.com"};
            //短信收件人, address为varag类型
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf<String>(address))

            intent.putExtra(Intent.EXTRA_SUBJECT, "Account：" + subjectContent) //设置短信主题
            if(intent.resolveActivity(activity.packageManager) != null) { //能正常找到目标Activity
                //选择电子邮件客户端
                activity.startActivity(Intent.createChooser(intent, "Seleccionar cliente de correo" + " " + "electrónico"))
            } else {
                //do something when startActivity failed. like this:
                //Toast.makeText(this, getResources().getString(R.string .software_relacionado_no_encontrado), Toast.LENGTH_SHORT).show()
            }
        } catch(e: Exception) {
            e.printStackTrace()
        }
    }


    /**
     * 打开手机联系人，并获取联系人相关信息
     * */
    fun openContacts(requestCode: Int, activity: Activity) { //调用本地联系人
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE
        activity.startActivityForResult(intent, requestCode)
    }

    //简单的获取联系人的电话号码或电子邮件地址或邮政地址
    /*
    static final int REQUEST_SELECT_CONTACT = 1;
    public void selectContact()
    { //action为Intent.ACTION_PICK
        Intent intent = new Intent(Intent.ACTION_PICK); //从有电话号码的联系人中选取
        intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE); //从有电子邮件地址的联系人中选取
        //        intent.setType(ContactsContract.CommonDataKinds.Email.CONTENT_TYPE);
        //从有邮政地址的联系人中选取。
        //        intent.setType(ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_TYPE);
        if(intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_SELECT_CONTACT);
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == RESULT_OK) {
            when(requestCode) {
                REQUEST_CODE_ONE -> { //回掉, 选择通讯录联系人返回
                    if(data == null) {
                        return@onActivityResult
                    }
                    val projection: Array<String> = arrayOf(ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
                    try {
                        val cursor: Cursor? = contentResolver.query(data.data!!, projection, null, null, null)
                        cursor?.let {
                            while(it.moveToNext()) {
                                val number: String = it.getString(0)
                                val displayName: String = it.getString(1)
                                binding.personalPhoneOneTv.text = number.apply {
                                    replace(" ", "")
                                    Pattern.compile("[^0-9]]")
                                        .matcher(this)
                                        .replaceAll("")
                                        .trim()
                                }
                                binding.personalNameOneEt.setText(displayName)
                            }
                            it.close()
                        }
                    } catch(e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
     */


    }

