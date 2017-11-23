var exec = require('cordova/exec');
var argscheck = require('cordova/argscheck');
var softImgPick = function() {}

softImgPick.prototype.requestReadPermission = function(callback) {
    argscheck.checkArgs("F", "softImgPick.requestReadPermission", arguments)
    return exec(callback, null, "softImgPick", "requestReadPermission", [])
}

/**
 * 显示操作
 * @param {*} callback 
 * @param {*} options 
 */
softImgPick.prototype.showList = function(done, err, options) {

    if (!options) {
        options = {}
    }

    var params = {
        //确定按钮文字
        ok_text = options.ok_text ? options.ok_text : "确定",
        //返回按钮文字
        cancel_text = options.cancel_text ? options.cancel_text : "返回",
        //勾选小图标
        check_img_res = options.check_img_res ? options.check_img_res : "",
        //确定和返回字的大小
        manager_title_text_size = options.manager_title_text_size ? options.manager_title_text_size : 10,
        //限制相册选择数量
        limit_num = options.limit_num ? options.limit_num : 9,
        //是否启动网络请求上传数据
        post_imgs = options.post_imgs ? options.post_imgs : true,
        //启动网络请求过后指定url
        path_url = options.path_url ? options.path_url : "",
        //文件上传默认的key
        file_key = options.file_key ? options.file_key : "files",
        //文件上传的参数
        photo_params = options.photo_params ? options.photo_params : {},
        //顶部banner的颜色
        banner_color = options.banner_color ? options.banner_color : "ffffff"
    }
    argscheck.checkArgs("FFO", "softImgPick.showList", arguments)
    return exec(done, err, "softImgPick", "showList", [params])

}




module.exports = new softImgPick();