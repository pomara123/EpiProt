package epiprot;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.ViewFactory;
import javax.swing.text.html.HTMLEditorKit;

class WrapEditorKit extends /*StyledEditorKit*/HTMLEditorKit {
    ViewFactory defaultFactory=new WrapColumnFactory();
    public ViewFactory getViewFactory() {
        return defaultFactory;
    }

    public MutableAttributeSet getInputAttributes() {
        MutableAttributeSet mAttrs=super.getInputAttributes();
        mAttrs.removeAttribute(WrapApp.LINE_BREAK_ATTRIBUTE_NAME);
        return mAttrs;
    }
}
