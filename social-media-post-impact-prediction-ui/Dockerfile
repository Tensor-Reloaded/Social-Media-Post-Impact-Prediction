FROM node:14-alpine as build
WORKDIR /usr/local/app
COPY ./package.json ./webpack.config.js /usr/local/app/
RUN npm install
COPY ./ /usr/local/app/
RUN npm run prod
FROM nginx:stable-alpine
COPY --from=build /usr/local/app/dist /usr/share/nginx/html
EXPOSE 80