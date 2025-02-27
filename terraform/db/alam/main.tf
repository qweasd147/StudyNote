data "aws_cloudwatch_log_group" "subscribe-group" {
  name = var.cloudwatch-group-name
}

resource "aws_lambda_function" "lambda-alam" {
  function_name = var.lambda-name

  filename         = "${path.module}/cw-subscription.zip"
  source_code_hash = filebase64sha256("${path.module}/cw-subscription.zip")

  role    = aws_iam_role.lambda_role.arn
  handler = "./src/alarm.handler"
  runtime = "nodejs18.x"
  // architectures = "x86_64"
  memory_size = 512
  timeout     = 6

  environment {
    variables = {
      env = "dev"
      stage : "dev"
    }
  }
}

resource "aws_iam_role" "lambda_role" {
  name = "${var.lambda-name}_lambda_exec_role"

  assume_role_policy = jsonencode({
    Version = "2012-10-17",
    Statement = [{
      Action = "sts:AssumeRole",
      Effect = "Allow",
      Principal = {
        Service = "lambda.amazonaws.com"
      }
    }]
  })
}

resource "aws_iam_role_policy_attachment" "lambda_policy" {
  role       = aws_iam_role.lambda_role.name
  policy_arn = "arn:aws:iam::aws:policy/service-role/AWSLambdaBasicExecutionRole"
}

resource "aws_cloudwatch_log_subscription_filter" "subscribe-slow-query" {
  name            = "subscribe_slow_query"
  log_group_name  = data.aws_cloudwatch_log_group.subscribe-group.name
  filter_pattern  = ""
  destination_arn = aws_lambda_function.lambda-alam.arn

  depends_on = [
    aws_lambda_permission.invoke-function
  ]
}

resource "aws_lambda_permission" "invoke-function" {
  statement_id  = "AllowExecutionFromCloudWatchLogs"
  action        = "lambda:InvokeFunction"
  function_name = aws_lambda_function.lambda-alam.function_name
  principal     = "logs.amazonaws.com"
  source_arn    = "${data.aws_cloudwatch_log_group.subscribe-group.arn}:*"
}

