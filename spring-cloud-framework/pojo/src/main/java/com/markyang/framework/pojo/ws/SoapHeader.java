package com.markyang.framework.pojo.ws;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.namespace.QName;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SoapHeader {

	private String id;

	private String userName;

	private String passWord;

	private String hospId;

	private String deptId;

	private String workerId;

	private String workerPwd;

	private Map<QName, String> otherAttributes;

}
