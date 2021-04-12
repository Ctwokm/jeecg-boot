```
  --存储过程模板案例
  procedure Test_doCall(v_jsonString in varchar2,
                        v_opCode     in varchar2,
                        v_resInfo    out varchar2,
                        v_resCode    out varchar2,
                        v_resMsg     out varchar2) is
  
    iv_tmp_jsonObj json;
  
  begin
    v_resCode := '0';
    v_resMsg  := 'ok！';
    v_resInfo := v_jsonString;
  
    iv_tmp_jsonObj := json(v_jsonString);
  
    --json_ext.get_string(iv_tmp_jsonObj, 'recordNo');
    --json_ext.get_string(iv_tmp_jsonObj, 'trainNo');
    
  exception
    when others then
      rollback;
      v_resCode := '1';
      v_resMsg  := '调用失败：' || substr(sqlerrm, 1, 100);
      return;
  end;
```
