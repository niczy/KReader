//
//  NewsListTableViewController.swift
//  goudu
//
//  Created by Yu Zhao on 8/23/14.
//  Copyright (c) 2014 Yu Zhao. All rights reserved.
//

import UIKit

class NewsListTableViewController: UITableViewController {
    
    var newsList: NSArray?

    override func viewDidLoad() {
        super.viewDidLoad()
        var nib = UINib(nibName: "NewsItemView", bundle: nil)
        self.tableView.registerNib(nib, forCellReuseIdentifier: "list")
        let url = NSURL(string: "http://www.nich01as.com/apps/kreader/_/articles?q=kobe,durant")
        let task = NSURLSession.sharedSession().dataTaskWithURL(url) {(data: NSData!, response, error) in
            if (error != nil) {
                println("request error \(error.localizedDescription)")
            } else {
                var dataStr = NSString(data: data, encoding: NSUTF8StringEncoding)
                var dataError: NSError?
                self.newsList = NSJSONSerialization.JSONObjectWithData(data, options: nil, error: &dataError) as NSArray?
                if (dataError != nil) {
                    println(dataError?.localizedDescription)
                } else {
                    self.tableView.reloadData()
                }
            }
            
            
        }
        task.resume()

        // Uncomment the following line to preserve selection between presentations
        // self.clearsSelectionOnViewWillAppear = false

//         Uncomment the following line to display an Edit button in the navigation bar for this view controller.
    //     self.navigationItem.rightBarButtonItem = self.editButtonItem()
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    // MARK: - Table view data source

    override func numberOfSectionsInTableView(tableView: UITableView!) -> Int {
        // #warning Potentially incomplete method implementation.
        // Return the number of sections.
        return 1
    }

    override func tableView(tableView: UITableView!, numberOfRowsInSection section: Int) -> Int {
        // #warning Incomplete method implementation.
        // Return the number of rows in the section.
        if (self.newsList != nil) {
            return self.newsList!.count
        } else {
            return 0
        }
    }

    
    override func tableView(tableView: UITableView!, cellForRowAtIndexPath indexPath: NSIndexPath!) -> UITableViewCell! {
        let cell = tableView.dequeueReusableCellWithIdentifier("list", forIndexPath: indexPath) as NewItemTableViewCell

        var newsItem = self.newsList!.objectAtIndex(indexPath.row) as NSDictionary
        
        println("news item \(newsItem)")
        cell.titleLabel!.text = newsItem.objectForKey("title") as String
        cell.contentLabel!.text = newsItem.objectForKey("content") as String

        return cell
    }


    /*
    // Override to support conditional editing of the table view.
    override func tableView(tableView: UITableView!, canEditRowAtIndexPath indexPath: NSIndexPath!) -> Bool {
        // Return NO if you do not want the specified item to be editable.
        return true
    }
    */

    /*
    // Override to support editing the table view.
    override func tableView(tableView: UITableView!, commitEditingStyle editingStyle: UITableViewCellEditingStyle, forRowAtIndexPath indexPath: NSIndexPath!) {
        if editingStyle == .Delete {
            // Delete the row from the data source
            tableView.deleteRowsAtIndexPaths([indexPath], withRowAnimation: .Fade)
        } else if editingStyle == .Insert {
            // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
        }    
    }
    */

    /*
    // Override to support rearranging the table view.
    override func tableView(tableView: UITableView!, moveRowAtIndexPath fromIndexPath: NSIndexPath!, toIndexPath: NSIndexPath!) {

    }
    */

    /*
    // Override to support conditional rearranging of the table view.
    override func tableView(tableView: UITableView!, canMoveRowAtIndexPath indexPath: NSIndexPath!) -> Bool {
        // Return NO if you do not want the item to be re-orderable.
        return true
    }
    */

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue!, sender: AnyObject!) {
        // Get the new view controller using [segue destinationViewController].
        // Pass the selected object to the new view controller.
    }
    */

}
