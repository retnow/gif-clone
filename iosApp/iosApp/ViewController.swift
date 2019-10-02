import UIKit
import FLAnimatedImage

class ViewController: UIViewController {
    var collectionView: UICollectionView?
    var urls: [String] = []
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        setupCollectionView()
        
        GIFRetriever().requestCatGIFs { [weak self] gifs in
            guard let self = self else { return }
            self.urls = gifs
            self.collectionView?.reloadData()
        }
    }
    
    func setupCollectionView() {
        let layout = UICollectionViewFlowLayout.init()
        layout.itemSize = view.bounds.size
        
        collectionView = UICollectionView(
            frame: view.bounds,
            collectionViewLayout: layout
        )
        
        collectionView?.register(
            CollectionViewCell.self,
            forCellWithReuseIdentifier: "GifCell"
        )
        
        collectionView?.dataSource = self
        collectionView?.delegate = self
        collectionView?.isPagingEnabled = true
        if let cv = collectionView { view.addSubview(cv) }
    }
}

extension ViewController: UICollectionViewDataSource {
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return urls.count
    }

    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        guard
            let data = urls[indexPath.row].urlData(),
            let cell = collectionView.dequeueReusableCell(
                withReuseIdentifier: "GifCell",
                for: indexPath) as? CollectionViewCell
            else { return UICollectionViewCell() }

        cell.setImage(data: data)
        return cell
    }
}

extension ViewController: UICollectionViewDelegateFlowLayout {
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, minimumLineSpacingForSectionAt section: Int) -> CGFloat {
        return 0
    }
}
