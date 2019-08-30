read -p "Enter ifsc : " ifsc
echo "Branch details in JSON response"
curl -H 'authorization:eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJGeWxlX1Rlc3QiLCJuYW1lIjoiVmlqYXkuQyIsImV4cCI6MTU2NDA3Mjk0MH0.aJnK8-3duMCJW5PGvxqtotIq6qySJ_1dz5f1KSoyP7o' 'https://desolate-journey-91406.herokuapp.com/vijay/BankDetails?ifsc='$ifsc

