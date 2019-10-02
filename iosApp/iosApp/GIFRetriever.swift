import Foundation
import GifLibrary

class GIFRetriever {
    func requestCatGIFs(_ closure: @escaping ([String]) -> Void) {
        GiphyAPI().getCatGIFLinks { gifs -> KotlinUnit in
            closure(gifs)
            return KotlinUnit()
        }
    }
}
