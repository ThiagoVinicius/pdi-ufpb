/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufpb.di.pdi.about;

/**
 *
 * @author thiago
 */
public class AboutManager {

    private About about;
    private Credits credits;
    private License license;

    private static AboutManager instance;

    private static void init () {
        instance = new AboutManager();
    }

    static {
        init();
    }

    private AboutManager () {
        about   = new About();
        credits = new Credits();
        license = new License();
    }

    public static AboutManager getInstance () {
        return instance;
    }

    public void showCredits () {
        credits.setVisible(true);
    }

    public void showAbout () {
        about.setVisible(true);
    }

    public void showLicense () {
        license.setVisible(true);
    }

}
