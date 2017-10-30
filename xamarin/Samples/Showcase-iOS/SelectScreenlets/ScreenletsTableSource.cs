using System;
using Foundation;
using UIKit;
using ShowcaseiOS.ViewController;
using System.Diagnostics;

namespace ShowcaseiOS.SelectScreenlets
{
    public class ScreenletsTableSource: UITableViewSource
    {
        protected string[] screenlets;
        protected string[] viewControllers;
        protected string cellIdentifier = "TableCell";
        SelectScreenletViewController owner;

        public ScreenletsTableSource(string[] data, string[] vcs, SelectScreenletViewController owner)
        {
            screenlets = data;
            viewControllers = vcs;
            this.owner = owner;
        }

        /* UITableViewSource */

        public override UITableViewCell GetCell(UITableView tableView, NSIndexPath indexPath)
        {
            UITableViewCell cell = tableView.DequeueReusableCell(cellIdentifier);

            if (cell == null)
            {
                cell = new UITableViewCell(UITableViewCellStyle.Default, cellIdentifier);
                cell.TextLabel.Text = screenlets[indexPath.Row];
                cell.Accessory = UITableViewCellAccessory.DisclosureIndicator;
            }

            return cell;
        }

        public override nint RowsInSection(UITableView tableview, nint section)
        {
            return screenlets.Length;
        }

        /* UITableView */

        public override void RowSelected(UITableView tableView, Foundation.NSIndexPath indexPath)
        {
            Debug.WriteLine($"Navigate to {screenlets[indexPath.Row]}");
            UIStoryboard board = UIStoryboard.FromName(screenlets[indexPath.Row], null);
            UIViewController vc = board.InstantiateViewController(viewControllers[indexPath.Row]);
            owner.NavigationController.PushViewController(vc, true);

            tableView.DeselectRow(indexPath, true);
        }
    }
}
