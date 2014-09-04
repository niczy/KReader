//
//  NewItemViewController.swift
//  goudu
//
//  Created by Yu Zhao on 8/24/14.
//  Copyright (c) 2014 Yu Zhao. All rights reserved.
//

import UIKit

class NewItemTableViewCell: UITableViewCell {
    @IBOutlet weak var titleLabel: UILabel!

    @IBOutlet weak var contentLabel: UILabel!
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
