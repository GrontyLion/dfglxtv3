package com.mjl.dfglxtv3.handler;

import cn.dev33.satoken.exception.DisableLoginException;
import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import com.mjl.dfglxtv3.common.Result;
import com.mjl.dfglxtv3.common.ResultCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常处理 
 */
@ControllerAdvice
public class GlobalException {

	// 全局异常拦截（拦截项目中的所有异常）
	@ResponseBody
	@ExceptionHandler
	public ModelAndView handlerException(Exception e, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// 打印堆栈，以供调试
		System.out.println("全局异常---------------");
		e.printStackTrace();

		ModelAndView error403 = new ModelAndView("/error/403");
		ModelAndView error404 = new ModelAndView("/error/404");
		ModelAndView error500 = new ModelAndView("/error/500");

		if (e instanceof NotLoginException) {	// 如果是未登录异常
			NotLoginException ee = (NotLoginException) e;
			ResponseEntity<Result<String>> body = ResponseEntity.status(HttpStatus.FORBIDDEN).body(Result.failed(ResultCode.USER_NOT_LOGIN, ee.getMessage()));
			error403.addObject("body", body);
			return error403;
		} 
		else if(e instanceof NotRoleException) {		// 如果是角色异常
			NotRoleException ee = (NotRoleException) e;
			ResponseEntity<Result<String>> body = ResponseEntity.status(HttpStatus.FORBIDDEN).body(Result.failed(ResultCode.ROLE_NOT_MATCH, ee.getMessage()));
			error403.addObject("body", body);
			return error403;
		} 
		else if(e instanceof NotPermissionException) {	// 如果是权限异常
			NotPermissionException ee = (NotPermissionException) e;
			ResponseEntity<Result<String>> body = ResponseEntity.status(HttpStatus.FORBIDDEN).body(Result.failed(ResultCode.NOT_PERMISSION, ee.getMessage()));
			error403.addObject("body", body);
			return error403;
		} 
		else if(e instanceof DisableLoginException) {	// 如果是被封禁异常
			DisableLoginException ee = (DisableLoginException) e;
			ResponseEntity<Result<String>> body = ResponseEntity.status(HttpStatus.FORBIDDEN).body(Result.failed(ResultCode.USER_BE_DISABLED, ee.getMessage()));
			error403.addObject("body", body);
			return error403;
		} 
		else {	// 普通异常, 输出：500 + 异常信息
			ResponseEntity<Result<String>> body = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.failed(ResultCode.FAILED, e.getMessage()));
			error500.addObject("body", body);
			return error500;

		}
	}
	
}