read -p "Enter city : " city
read -p "Enter bank name : " bankName
read -p "enter offset: " offset
read -p "enter limit: " limit
rep="%20"
API="https://desolate-journey-91406.herokuapp.com/vijay/branchDetails?city="$city"&bankName="${bankName// /$rep}"&offset="$offset"&limit="$limit
echo "API-- "$API
echo "Bank details in JSON response"
curl -H 'authorization: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJmeWxlQXV0aCIsIm5hbWUiOiJ2aWpheSBDIiwiaWF0IjoxNTE2MjM5MDIyLCJleHAiOjE1Njc2OTg3NDMwMDB9.SKk92ZE_25-3RCveuY8GDHVAc7mhud9tOZk47UFMxPQ' "https://desolate-journey-91406.herokuapp.com/vijay/branchDetails?city="$city"&bankName="${bankName// /$rep}"&offset="$offset"&limit="$limit