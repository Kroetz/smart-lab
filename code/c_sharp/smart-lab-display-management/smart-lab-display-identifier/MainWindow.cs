using System;
using System.Windows.Forms;

namespace smart_lab_display_identifier
{
    public partial class MainWindow : Form
    {
        public MainWindow()
        {
            InitializeComponent();
        }

        private void showButton_Click(object sender, EventArgs e)
        {
            SmartLabDisplayIdentifier.ShowDisplayNameBadges();
        }

        private void hideButton_Click(object sender, EventArgs e)
        {
            SmartLabDisplayIdentifier.HideDisplayNameBadges();
        }
    }
}
