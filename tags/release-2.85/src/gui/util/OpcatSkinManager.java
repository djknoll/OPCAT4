package gui.util;

/*jadclipse*/// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
//Jad home page: http://www.geocities.com/kpdus/jad.html
//Decompiler options: packimports(3) radix(10) lradix(10) 

import com.l2fprod.gui.plaf.skin.SkinLookAndFeel;
import com.sciapp.f.j;
import java.awt.Component;
import java.net.URL;
import java.util.Iterator;
import java.util.TreeMap;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class OpcatSkinManager
{
 private class a
     implements Runnable
 {

     public synchronized void run()
     {
         SwingUtilities.updateComponentTreeUI(OpcatSkinManager.this._fldif);
     }

     private a()
     {
     }

 }


 private OpcatSkinManager()
 {
     this.a = null;
     this._fldbyte = null;
     this._fldtry = null;
     //b._mthif();
     this.a();
 }

 public String getCurrentTheme()
 {
     return this._fldint;
 }

 public String[] getThemes()
 {
     j j1 = new j();
     for(Iterator iterator = this.a.keySet().iterator(); iterator.hasNext(); j1.a(iterator.next().toString())) {
		;
	}
     for(Iterator iterator1 = this._fldbyte.keySet().iterator(); iterator1.hasNext(); j1.a(iterator1.next().toString())) {
		;
	}
     return j1.a();
 }

 private void a()
 {
     this.a = new TreeMap();
     this._fldbyte = new TreeMap();
     this._fldtry = new a();
     javax.swing.UIManager.LookAndFeelInfo alookandfeelinfo[] = UIManager.getInstalledLookAndFeels();
     for(int i = 0; i < alookandfeelinfo.length; i++) {
		try
         {
             Class class1 = Class.forName(alookandfeelinfo[i].getClassName());
             LookAndFeel lookandfeel = (LookAndFeel)class1.newInstance();
             if(lookandfeel.isSupportedLookAndFeel()) {
				this.a.put(lookandfeel.getName(), lookandfeel.getClass());
			}
         }
         catch(Exception exception1)
         {
             System.out.println(exception1);
         }
	}

     this._fldint = UIManager.getLookAndFeel().getName();
     try
     {
         this.a("aqua", "aquathemepack.zip");
         this.a("bbj", "bbjthemepack.zip");
         this.a("beos", "beosthemepack.zip");
         this.a("cellshaded", "cellshadedthemepack.zip");         
         this.a("corona", "coronaHthemepack.zip");
         this.a("crystal2", "crystal2themepack.zip");
         this.a("gorilla", "gorillathemepack.zip");
         this.a("macos", "macosthemepack.zip");
         this.a("macos tiger", "tigerthemepack.zip");         
         this.a("midnight", "midnightthemepack.zip");
         this.a("modern", "modernthemepack.zip");
         this.a("opusOSDeep", "opusOSDeepthemepack.zip");
         this.a("theme", "themepack.zip");
         this.a("toxic", "toxicthemepack.zip");
         this.a("underling", "underlingthemepack.zip");
         this.a("whistler", "whistlerthemepack.zip");
         this.a("xpluna", "xplunathemepack.zip");
     }
     catch(Exception exception) { }
 }

 private void a(String s, String s1)
 {
     String s2 = _fldfor + s1;
     URL url = this.getClass().getResource(s2);
     if(url != null) {
		this._fldbyte.put(_flddo + s, url);
	}
 }

 public static OpcatSkinManager instance()
 {
     return _fldnew;
 }

 public void setMainComponent(Component component)
 {
     this._fldif = component;
 }

 public boolean setTheme(String s)
 {
     boolean flag = true;
     flag = this.a(s);
     return flag;
 }

 private boolean a(String s)
 {
     if(s == null) {
		throw new NullPointerException();
	}
     if(s.equals(this._fldint)) {
		return false;
	}
     boolean flag = true;
     LookAndFeel lookandfeel = UIManager.getLookAndFeel();
     try
     {
         if(s.startsWith("skin"))
         {
             URL url = (URL)this._fldbyte.get(s);
             flag = url != null;
             if(flag)
             {
                 SkinLookAndFeel.setSkin(SkinLookAndFeel.loadThemePack(url));
                 UIManager.setLookAndFeel(new SkinLookAndFeel());
                 this._fldint = s;
             }
         } else
         {
             Class class1 = (Class)this.a.get(s);
             if(class1 != null)
             {
                 LookAndFeel lookandfeel1 = (LookAndFeel)class1.newInstance();
                 UIManager.setLookAndFeel(lookandfeel1);
                 this._fldint = s;
             }
         }
     }
     catch(Exception exception)
     {
         //com.sciapp.b.b._mthif(exception);
         flag = false;
         try
         {
             if(UIManager.getLookAndFeel() != lookandfeel) {
				UIManager.setLookAndFeel(lookandfeel);
			}
         }
         catch(Exception exception1)
         {
             //com.sciapp.b.b._mthif(exception1);
         }
     }
     if(flag && (this._fldif != null)) {
		SwingUtilities.invokeLater(this._fldtry);
	}
     return flag;
 }

 private TreeMap a;
 private TreeMap _fldbyte;
 private static String _fldfor = "/skins/";
 private static String _flddo = "skin - ";
 private String _fldint;
 private Component _fldif;
 private a _fldtry;
 private static OpcatSkinManager _fldnew = null;

 static 
 {
     _fldnew = new OpcatSkinManager();
     //b._mthif();
 }

}



/***** DECOMPILATION REPORT *****

	DECOMPILED FROM: /home/raanan/My Documents/Projects/Opcat/Opcat/trunk/lib/tablelib.jar


	TOTAL TIME: 23 ms


	JAD REPORTED MESSAGES/ERRORS:


	EXIT STATUS:	0


	CAUGHT EXCEPTIONS:

********************************/