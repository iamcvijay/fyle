read -p "Enter city : " city
read -p "Enter bank name : " bankName
rep="%20"
echo "Bank details in JSON response"
curl -H 'authorization: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJGeWxlX1Rlc3QiLCJuYW1lIjoiVmlqYXkuQyIsImV4cCI6MTU2NDA3Mjk0MH0.aJnK8-3duMCJW5PGvxqtotIq6qySJ_1dz5f1KSoyP7o' "https://desolate-journey-91406.herokuapp.com/vijay/branchDetails?city="$city"&bankName="${bankName// /$rep}