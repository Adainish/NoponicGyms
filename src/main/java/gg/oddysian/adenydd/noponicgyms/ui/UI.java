package gg.oddysian.adenydd.noponicgyms.ui;

import java.util.ArrayList;
import java.util.List;

public class UI {



    public List<String> getFormattedList(List<String> lore) {
        List<String> formattedInfo = new ArrayList<>();
        for (String s: lore) {
            formattedInfo.add(s.replaceAll("&", "§"));
        }
        return formattedInfo;
    }
}
