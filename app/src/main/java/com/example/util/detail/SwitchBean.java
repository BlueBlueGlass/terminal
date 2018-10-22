package com.example.util.detail;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/9/6.
 */

public class SwitchBean extends ParentButton implements Serializable {
   private String checkedButtonName;
    private String switchName;
    private String switchOnSendText;
    private String switchOffSendText;

    public SwitchBean(String checkedButtonName, String switchName, String switchOnSendText,String switchOffSendText) {
        this.checkedButtonName = checkedButtonName;
        this.switchName = switchName;
        this.switchOnSendText = switchOnSendText;
        this.switchOffSendText=switchOffSendText;
    }

    public String getCheckedButtonName() {
        return checkedButtonName;
    }

    public String getSwitchName() {
        return switchName;
    }
    public String getSwitchOnSendText() {
        return switchOnSendText;
    }
    public String getSwitchOffSendText() {
        return switchOffSendText;
    }
}
