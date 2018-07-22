using System;
using System.Collections.Generic;
using System.Drawing;
using System.Linq;
using System.Runtime.InteropServices;
using System.Windows.Forms;

namespace smart_lab_window_handling
{
    public static class WindowUtils
    {
        [DllExport]
        public static IntPtr FindWindowByTitle(string windowTitle)
        {
            return FindWindow(null, windowTitle);
        }

        [DllExport]
        public static IntPtr FindForegroundWindow()
        {
            return GetForegroundWindow();
        }

        public static void MaximizeWindow(IntPtr windowHandle)
        {
            if(!ShowWindow(windowHandle, SW.MAXIMIZE)) throw new Exception("Could not maximize window");
        }

        [DllExport]
        public static void MoveToFullScreen(IntPtr windowHandle, string screenName)
        {
            Screen screen = ScreenFromName(screenName);
            MoveToFullScreen(windowHandle, screen);
        }

        public static void MoveToFullScreen(IntPtr windowHandle, Screen screen)
        {
            var monitor = screen.WorkingArea;
            MoveToScreen(
                    windowHandle,
                    HWND.TOP,
                    monitor.Left,
                    monitor.Top,
                    monitor.Width,
                    monitor.Height,
                    SWP.NOFLAGS,
                    screen);
            MaximizeWindow(windowHandle);
        }

        public static void MoveToScreenCenter(IntPtr windowHandle, Screen screen)
        {
            var monitor = screen.WorkingArea;
            var windowDimensions = WindowDimensions(windowHandle);
            MoveToScreen(
                windowHandle,
                HWND.TOP,
                monitor.Left + monitor.Width / 2 - windowDimensions.Width / 2,
                monitor.Top + monitor.Height / 2 - windowDimensions.Height / 2,
                0,  // Is ignored because of SWP.NOSIZE
                0,  // Is ignored because of SWP.NOSIZE
                SWP.NOSIZE,
                screen);
        }

        public static Rectangle WindowDimensions(IntPtr windowHandle)
        {
            RECT windowRect;
            if (!GetWindowRect(windowHandle, out windowRect)) throw new Exception("Could not get window rect");
            return new Rectangle(
                windowRect.Left,
                windowRect.Top,
                windowRect.Right - windowRect.Left + 1,
                windowRect.Bottom - windowRect.Top + 1);
        }

        private static void MoveToScreen(IntPtr hWnd, IntPtr hWndInsertAfter, int x, int y, int cx, int cy, uint uFlags, Screen screen)
        {
            if(!SetWindowPos(hWnd, hWndInsertAfter, x, y, cx, cy, uFlags)) throw new Exception("Could not move window");
        }

        private static Screen ScreenFromName(string screenName)
        {
            List<Screen> screens = Screen.AllScreens
                .Where(screen => screen.DeviceName.Equals(screenName))
                .ToList();
            if (screens.Count < 1) throw new Exception("No screen with the specified name found");
            if (screens.Count > 1) throw new Exception("Multiple screens with the specified name found");
            return screens.First();
        }

        /*
         * NOTE: The prototypes of imported functions must be listed below methods with the attribute [DllExport] for DllExport to work.
         */

        [DllImport("user32.dll")]
        static extern IntPtr FindWindow(string lpClassName, string lpWindowName);

        [DllImport("user32.dll")]
        private static extern IntPtr GetForegroundWindow();

        [DllImport("user32.dll")]
        [return: MarshalAs(UnmanagedType.Bool)]
        static extern bool ShowWindow(IntPtr hWnd, int nCmdShow);

        [DllImport("user32.dll")]
        [return: MarshalAs(UnmanagedType.Bool)]
        private static extern bool SetWindowPos(IntPtr hWnd, IntPtr hWndInsertAfter, int x, int y, int cx, int cy, uint uFlags);

        [DllImport("user32.dll")]
        [return: MarshalAs(UnmanagedType.Bool)]
        static extern bool GetWindowRect(IntPtr hWnd, out RECT lpRect);
    }

    /// <summary>
    /// ShowWindow: Possible values for nCmdShow
    /// </summary>
    public static class SW
    {
        public static readonly int FORCEMINIMIZE = 11;
        public static readonly int HIDE = 0;
        public static readonly int MAXIMIZE = 3;
        public static readonly int MINIMIZE = 6;
        public static readonly int RESTORE = 9;
        public static readonly int SHOW = 5;
        public static readonly int SHOWDEFAULT = 10;
        public static readonly int SHOWMAXIMIZED = 3;
        public static readonly int SHOWMINIMIZED = 2;
        public static readonly int SHOWMINNOACTIVE = 7;
        public static readonly int SHOWNA = 8;
        public static readonly int SHOWNOACTIVATE = 4;
        public static readonly int SHOWNORMAL = 1;
    }

    /// <summary>
    /// SetWindowPos: Possible values for hWndInsertAfter
    /// </summary>
    public static class HWND
    {
        public static readonly IntPtr TOPMOST = new IntPtr(-1);
        public static readonly IntPtr NOTOPMOST = new IntPtr(-2);
        public static readonly IntPtr TOP = new IntPtr(0);
        public static readonly IntPtr BOTTOM = new IntPtr(1);
    }

    /// <summary>
    /// SetWindowPos Possible values for uFlags
    /// </summary>
    public static class SWP
    {
        public static readonly uint NOFLAGS = 0x0000;
        public static readonly uint NOSIZE = 0x0001;
        public static readonly uint NOMOVE = 0x0002;
        public static readonly uint NOZORDER = 0x0004;
        public static readonly uint NOREDRAW = 0x0008;
        public static readonly uint NOACTIVATE = 0x0010;
        public static readonly uint DRAWFRAME = 0x0020;
        public static readonly uint FRAMECHANGED = 0x0020;
        public static readonly uint SHOWWINDOW = 0x0040;
        public static readonly uint HIDEWINDOW = 0x0080;
        public static readonly uint NOCOPYBITS = 0x0100;
        public static readonly uint NOOWNERZORDER = 0x0200;
        public static readonly uint NOREPOSITION = 0x0200;
        public static readonly uint NOSENDCHANGING = 0x0400;
        public static readonly uint DEFERERASE = 0x2000;
        public static readonly uint ASYNCWINDOWPOS = 0x4000;
    }

    [StructLayout(LayoutKind.Sequential)]
    public struct RECT
    {
        public int Left;
        public int Top;
        public int Right;
        public int Bottom;
    }
}
