package com.markyang.framework.app.system.exception;

import com.markyang.framework.pojo.common.exception.FrameworkException;

/**
 * 系统异常
 *
 * @author yangchangliang
 * @version 1
 * @date 2020/05/07 2:48 下午
 */
public class SystemException extends FrameworkException {
  /**
   * Constructs a new runtime exception with the specified detail message.
   * The cause is not initialized, and may subsequently be initialized by a
   * call to {@link #initCause}.
   *
   * @param message the detail message. The detail message is saved for
   *                later retrieval by the {@link #getMessage()} method.
   */
  public SystemException(String message) {
    super(message);
  }

  /**
   * Constructs a new runtime exception with the specified detail message and
   * cause.  <p>Note that the detail message associated with
   * {@code cause} is <i>not</i> automatically incorporated in
   * this runtime exception's detail message.
   *
   * @param message the detail message (which is saved for later retrieval
   *                by the {@link #getMessage()} method).
   * @param cause   the cause (which is saved for later retrieval by the
   *                {@link #getCause()} method).  (A <tt>null</tt> value is
   *                permitted, and indicates that the cause is nonexistent or
   *                unknown.)
   * @since 1.4
   */
  public SystemException(String message, Throwable cause) {
    super(message, cause);
  }
}
