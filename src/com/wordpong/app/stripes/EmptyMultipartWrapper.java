package com.wordpong.app.stripes;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import net.sourceforge.stripes.config.Configuration;
import net.sourceforge.stripes.controller.FileUploadLimitExceededException;
import net.sourceforge.stripes.controller.multipart.MultipartWrapper;
import net.sourceforge.stripes.config.ConfigurableComponent;
import net.sourceforge.stripes.controller.multipart.MultipartWrapperFactory;

/**
 * GAE does not support file uploading so we need to disable this feature from Stripes.
 *
 * @author 110j
 */
public class EmptyMultipartWrapper implements ConfigurableComponent, MultipartWrapperFactory {

	/**
	 * @see net.sourceforge.stripes.config.ConfigurableComponent#init(net.sourceforge.stripes.config.Configuration)
	 */
	public void init(Configuration conf) throws Exception {
	}

	/**
	 * @see net.sourceforge.stripes.controller.multipart.MultipartWrapperFactory#wrap(javax.servlet.http.HttpServletRequest)
	 */
	public MultipartWrapper wrap(HttpServletRequest request) throws IOException, FileUploadLimitExceededException {
		return null;
	}
}