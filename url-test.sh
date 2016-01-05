curl -H "Content-Type: application/json" -X POST -d '{"username":"user1", "password":"1234", "fullname":"user 1"}' http://localhost:8888/users
curl -H "Content-Type: application/json" -X POST -d '{"username":"user2", "password":"1234", "fullname":"user 2"}' http://localhost:8888/users
curl -H "Content-Type: application/json" -X POST -d '{"username":"user3", "password":"1234", "fullname":"user 3"}' http://localhost:8888/users

curl -H "Content-Type: application/json" -X POST -d '{"name":"place1", "menuItems":[{"name":"item1", "price":10}]}' http://localhost:8888/places
curl -H "Content-Type: application/json" -X POST -d '{"name":"place2", "menuItems":[{"name":"item1", "price":10}, {"name":"item2", "price":20}]}' http://localhost:8888/places
curl -H "Content-Type: application/json" http://localhost:8888/places

curl -H "Content-Type: application/json" -X POST -d '' http://localhost:8888/votes/places/1/users/1
curl -H "Content-Type: application/json" -X POST -d '' http://localhost:8888/votes/places/1/users/2
curl -H "Content-Type: application/json" -X POST -d '' http://localhost:8888/votes/places/2/users/3

curl -H "Content-Type: application/json" http://localhost:8888/votescores
# curl -H "Content-Type: application/json" http://localhost:8888/votescores?date=2016-01-05T00:00:00