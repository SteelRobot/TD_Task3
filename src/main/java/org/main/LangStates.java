package org.main;

public enum LangStates {
    ENGLISH, RUSSIAN;

    public static LangStates langState = RUSSIAN;

    public static void SetLangState(LangStates langState) {
        LangStates.langState = langState;
    }

}
