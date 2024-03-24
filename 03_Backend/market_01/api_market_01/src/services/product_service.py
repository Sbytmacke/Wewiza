from api_market_01.src.models.product import Product
from api_market_01.src.repositories.product_repository import ProductRepository
import json


class ProductService:
    def __init__(self, product_repository: ProductRepository):
        self.product_repository = product_repository

    def get_all_products(self):
        result = self.product_repository.get_all_products()

        if result.is_failure():
            print("Failed to get all products:", result.error)
            return []

        products_list_json = result.value

        # Convert ObjectId to String
        for product in products_list_json:
            product["_id"] = str(product["_id"])

        return products_list_json

    def create_product_to_mongo_recieving_json(self, product_json: str):
        # print(json.dumps(product_json, indent=4))
        print(product_json)

        result = self.product_repository.insert_product(product_json)
        if result.is_failure():
            print("Failed to insert product:", result.error)
            return result.error
        else:
            return result.value
