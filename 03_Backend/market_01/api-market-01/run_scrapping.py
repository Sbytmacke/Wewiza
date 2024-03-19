from selenium import webdriver
from selenium.webdriver.chrome.service import Service
from src.services.scrapping_service import ScrappingService
from src.services.product_service import ProductService
from src.database.database_manager import DatabaseManager
from src.repositories.product_repository import ProductRepository

CONNECTION_MONGO = "mongodb://root:root@localhost:27021"
DATABASE_NAME = "mercadona"
COLLECTION_NAME = "products"

# Chrome Service
service = Service()
options = webdriver.ChromeOptions()
driver_chrome = webdriver.Chrome(service=service, options=options)

# Fachade
database_manager = DatabaseManager(CONNECTION_MONGO, DATABASE_NAME)
product_repository = ProductRepository(database_manager, COLLECTION_NAME)

# Services
product_service = ProductService(product_repository)
scrapping_service = ScrappingService(driver_chrome, product_service)
scrapping_service.run_simulation(40, 45)