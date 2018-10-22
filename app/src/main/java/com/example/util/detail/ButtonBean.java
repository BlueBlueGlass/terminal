package com.example.util.detail;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/9/8.
 */

public class ButtonBean extends ParentButton implements Serializable {
    private String checkedButtonName;
    private String buttonName;
    private String buttonSendText;

    public ButtonBean(String checkedButtonName, String buttonName, String buttonSendText) {
        this.checkedButtonName = checkedButtonName;
        this.buttonName = buttonName;
        this.buttonSendText = buttonSendText;
    }

    public String getCheckedButtonName() {
        return checkedButtonName;
    }

    public String getButtonName() {
        return buttonName;
    }

    public String getButtonSendText() {
        return buttonSendText;
    }
}
