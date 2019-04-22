package cn.tedu.store.controller;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.ExceptionHandler;

import cn.tedu.store.controller.ex.FileContentTypeException;
import cn.tedu.store.controller.ex.FileEmptyException;
import cn.tedu.store.controller.ex.FileIOException;
import cn.tedu.store.controller.ex.FileIllegalStateException;
import cn.tedu.store.controller.ex.FileSizeException;
import cn.tedu.store.controller.ex.FileUploadException;
import cn.tedu.store.service.ex.AccessDeniedException;
import cn.tedu.store.service.ex.AddressNotFoundException;
import cn.tedu.store.service.ex.DeleteException;
import cn.tedu.store.service.ex.InsertException;
import cn.tedu.store.service.ex.PasswordNotMatchException;
import cn.tedu.store.service.ex.ServiceException;
import cn.tedu.store.service.ex.UpdateException;
import cn.tedu.store.service.ex.UserNotFoundException;
import cn.tedu.store.service.ex.UsernameDuplicateException;
import cn.tedu.store.util.ResponseResult;

/**
 * 控制器类的基类
 */
public abstract class BaseController {
	
	/**
	 * 响应结果的状态：成功
	 */
	public static final Integer SUCCESS = 200;
	
	/**
	 * 从Session获取当前登录的用户id
	 * @param session HttpSession对象
	 * @return 当前登录的用户id
	 */
	protected final Integer getUidFromSession(HttpSession session) {
		return Integer.valueOf(session.getAttribute("uid").toString());
	}

	/**
	 * 统一处理异常
	 * @param e
	 * @return
	 */
	@ExceptionHandler({ServiceException.class
		, FileUploadException.class})
	public ResponseResult<Void> handleException(Throwable e) {
		ResponseResult<Void> rr
			= new ResponseResult<>();
		rr.setMessage(e.getMessage());
		
		if (e instanceof UsernameDuplicateException) {
			// 400-用户名冲突
			rr.setState(400);
		} else if (e instanceof UserNotFoundException) {
			// 401-用户数据不存在
			rr.setState(401);
		} else if (e instanceof PasswordNotMatchException) {
			// 402-验证密码失败
			rr.setState(402);
		} else if (e instanceof AddressNotFoundException) {
			// 403-收货地址数据不存在的异常
			rr.setState(403);
		} else if (e instanceof AccessDeniedException) {
			// 404-拒绝访问，可能因为权限不足，或数据归属有误
			rr.setState(404);
		} else if (e instanceof InsertException) {
			// 500-插入数据异常
			rr.setState(500);
		} else if (e instanceof UpdateException) {
			// 501-更新数据异常
			rr.setState(501);
		} else if (e instanceof DeleteException) {
			// 502-删除数据异常
			rr.setState(502);
		} else if (e instanceof FileEmptyException) {
			// 600-上传文件时没有选择文件或选中的文件为空时的异常
			rr.setState(600);
		} else if (e instanceof FileSizeException) {
			// 601-上传文件时文件大小超出限制异常
			rr.setState(601);
		} else if (e instanceof FileContentTypeException) {
			// 602-上传文件时文件类型异常
			rr.setState(602);
		} else if (e instanceof FileIllegalStateException) {
			// 603-上传文件时非法状态异常
			rr.setState(603);
		} else if (e instanceof FileIOException) {
			// 604-上传文件时读写异常
			rr.setState(604);
		}
		
		return rr;
	}
	
}
