using smart_lab_window_handling;
using System;
using System.Collections.Generic;
using System.Windows.Forms;

namespace smart_lab_display_identifier
{
    static class SmartLabDisplayIdentifier
    {
        private static List<DisplayNameBadge> openWindows = new List<DisplayNameBadge>();

        /// <summary>
        /// Main entry point for the application.
        /// </summary>
        [STAThread]
        static void Main()
        {
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            Application.Run(new MainWindow());
        }

        internal static void ShowDisplayNameBadges()
        {
            var screens = Screen.AllScreens;
            foreach (var screen in screens)
            {
                var displayNameBadge = new DisplayNameBadge();
                displayNameBadge.SetText(String.Format("This is display \"{0}\"", screen.DeviceName));
                IntPtr windowHandle = displayNameBadge.Handle;
                WindowUtils.MoveToScreenCenter(windowHandle, screen);
                openWindows.Add(displayNameBadge);
                displayNameBadge.Show();
            }
        }

        internal static void HideDisplayNameBadges()
        {
            foreach(var displayNameBadge in openWindows)
            {
                displayNameBadge.Close();
            }
            openWindows.Clear();
        }
    }
}
