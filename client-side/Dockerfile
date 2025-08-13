# Node image hỗ trợ multi-arch, không cần BUILDPLATFORM
FROM --platform=linux/arm64 node:20-alpine  AS build

WORKDIR /app

# Copy package files và cài dependencies
COPY package*.json ./
RUN npm ci

# Copy toàn bộ source code và build
COPY . .
RUN npm run build


# Stage 2: Serve static
FROM --platform=linux/arm64 node:20-alpine
WORKDIR /app

# Cài serve toàn cục
RUN npm install -g serve

# Copy file build từ stage 1
COPY --from=build /app/dist ./dist

EXPOSE 3000
CMD ["npx", "serve", "-s", "dist", "-l", "3000"]


