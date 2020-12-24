package com.markyang.framework.service.core.controller.support;

import com.markyang.framework.service.core.controller.hook.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 钩子支持有类
 * @author yangchangliang
 */
@Component
public final class ControllerHookHolder {

	public static List<PostGetHook> postGetHooks;
	public static List<PreAddHook> preAddHooks;
	public static List<PreUpdateHook> preModifies;
	public static List<PreDeleteHook> preDeleteHooks;
	public static List<PostAddHook> postAddHooks;
	public static List<PostUpdateHook> postModifies;
	public static List<PostDeleteHook> postDeleteHooks;

	public ControllerHookHolder(
		List<PostGetHook> postGetHooks,
		List<PreAddHook> preAddHooks,
		List<PreUpdateHook> preModifies,
		List<PreDeleteHook> preDeleteHooks,
		List<PostAddHook> postAddHooks,
		List<PostUpdateHook> postModifies,
		List<PostDeleteHook> postDeleteHooks) {
		ControllerHookHolder.postGetHooks = postGetHooks;
		ControllerHookHolder.preAddHooks = preAddHooks;
		ControllerHookHolder.preModifies = preModifies;
		ControllerHookHolder.preDeleteHooks = preDeleteHooks;
		ControllerHookHolder.postAddHooks = postAddHooks;
		ControllerHookHolder.postModifies = postModifies;
		ControllerHookHolder.postDeleteHooks = postDeleteHooks;
	}

}
