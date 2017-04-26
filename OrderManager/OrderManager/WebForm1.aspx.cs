using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Xml;

namespace OrderManager
{
    public partial class WebForm1 : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {

        }

        protected void Button1_Click(object sender, EventArgs e)
        {
            int outPut1;
            int outPut2;
            int outPut3;

            if (Int32.TryParse(Amount1.Text, out outPut1) && Int32.TryParse(Amount2.Text, out outPut2) && Int32.TryParse(Amount3.Text, out outPut3))
            {

                using (XmlWriter writer = XmlWriter.Create(@"D:\Order.xml"))
                {
                    writer.WriteStartDocument();
                    writer.WriteStartElement("Order");

                    writer.WriteElementString("Type", Item1.Text);
                    writer.WriteElementString("Amount", "" + outPut1);

                    writer.WriteElementString("Type", Item2.Text);
                    writer.WriteElementString("Amount", "" + outPut2);

                    writer.WriteElementString("Type", Item3.Text);
                    writer.WriteElementString("Amount", "" + outPut3);

                    writer.WriteEndElement();
                    writer.WriteEndDocument();
                }


            }
            else
            {
                Amount1.Text = "Error";
                Amount2.Text = "Error";
                Amount3.Text = "Error";
            }
                
        }

        protected void AddOrder_Click(object sender, EventArgs e)
        {

        }
    }
}