using System.Windows.Forms;

namespace smart_lab_display_identifier
{
    public partial class DisplayNameBadge : Form
    {
        public DisplayNameBadge()
        {
            InitializeComponent();
        }

        public void SetText(string text)
        {
            this.text.Text = text;
        }
    }
}
