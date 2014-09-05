//
//  NewsDetailViewController.swift
//  goudu
//
//  Created by Yu Zhao on 9/4/14.
//  Copyright (c) 2014 Yu Zhao. All rights reserved.
//

import UIKit

class NewsDetailViewController: UIViewController {
    
    @IBOutlet weak var contentText: UITextView!
    @IBOutlet weak var titleBar: UINavigationItem!
  

    var content = "aa"
    var titleStr = ""

    override func viewDidLoad() {
        super.viewDidLoad()
        self.contentText.text = content
        self.navigationItem.title = titleStr
        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue!, sender: AnyObject!) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */
    


}
