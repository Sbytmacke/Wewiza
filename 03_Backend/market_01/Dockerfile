FROM python:3.11-bullseye

WORKDIR /market_01

COPY ./api_market_01/requirements.txt ./
RUN pip install --no-cache-dir -r requirements.txt

EXPOSE 8081

# Ruta actual copiar a la ruta de destino
COPY . .

CMD [ "python", "./api_market_01/main.py" ]
# Esto es apra que la API se quede siempre activa, en caso de no utilizar ninguna dependencia de SERVER
#CMD [ "sh", "-c", "python ./main.py & tail -f /dev/null" ] 

