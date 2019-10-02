import Foundation

extension String {
    func urlData() -> Data? {
        if let url = URL(string: self),
            let data = try? Data(contentsOf: url) {
            return data
        }
        return nil
    }
}
