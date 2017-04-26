<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="WebForm1.aspx.cs" Inherits="OrderManager.WebForm1" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title></title>
</head>
<body>
    <form id="form1" runat="server">
        <div>
            <asp:TextBox ID="Item1" runat="server" Width="46px" Text="Item1" ReadOnly="true"></asp:TextBox>
            &nbsp;&nbsp; Amount:&nbsp;&nbsp;&nbsp;&nbsp;
        <asp:TextBox ID="Amount1" runat="server" Height="19px" Width="62px" AutoPostBack="False"></asp:TextBox>
            &nbsp;&nbsp;&nbsp;            
        </div>

        <div>
            <asp:TextBox ID="Item2" runat="server" Width="46px" Text="Item2" ReadOnly="true"></asp:TextBox>
            &nbsp;&nbsp; Amount:&nbsp;&nbsp;&nbsp;&nbsp;
        <asp:TextBox ID="Amount2" runat="server" Height="19px" Width="62px" AutoPostBack="False"></asp:TextBox>
            &nbsp;&nbsp;&nbsp;            
        </div>


        <div>
            <asp:TextBox ID="Item3" runat="server" Width="46px" Text="Item3" ReadOnly="true"></asp:TextBox>
            &nbsp;&nbsp; Amount:&nbsp;&nbsp;&nbsp;&nbsp;
        <asp:TextBox ID="Amount3" runat="server" Height="19px" Width="62px" AutoPostBack="False"></asp:TextBox>
            &nbsp;&nbsp;&nbsp;            
        </div>


        <p>
            <asp:Button ID="Button1" runat="server" Text="Send" OnClick="Button1_Click" />
        </p>
    </form>
</body>
</html>
