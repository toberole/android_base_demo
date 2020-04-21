// IZeusService.aidl
package com.zw.ipc;

import com.zw.ipc.Request;
import com.zw.ipc.Response;

interface IZeusService {
    Response postRequest(in Request request);
}
