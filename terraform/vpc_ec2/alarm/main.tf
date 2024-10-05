data "aws_lambda_function" "alaram_function_name" {
  function_name = var.alaram_lambda_name
}

resource "aws_sns_topic" "alb_health_check_failure_topic" {
  name = "ALB_HealthCheck_Failure_Topic"
}

resource "aws_cloudwatch_metric_alarm" "alb_health_check_failure_alarm" {
  alarm_name          = "ALB_HealthCheck_Failure_Alarm"
  comparison_operator = "LessThanThreshold"
  evaluation_periods  = "1"
  metric_name         = "HealthyHostCount"
  namespace           = "AWS/ApplicationELB"
  period              = "60"
  statistic           = "Minimum"
  threshold           = "1"
  treat_missing_data  = "ignore"
  alarm_description   = "Alarm when ALB target group has less than 1 unhealthy hosts"
  ok_actions          = [aws_sns_topic.alb_health_check_failure_topic.arn]
  alarm_actions       = [aws_sns_topic.alb_health_check_failure_topic.arn]

  dimensions = {
    LoadBalancer = var.target_load_balance_arn_suffix
    TargetGroup  = var.target_target_group_arn_suffix
  }
}

resource "aws_sns_topic_subscription" "alaram_lambda_subscription" {
  topic_arn = aws_sns_topic.alb_health_check_failure_topic.arn
  protocol  = "lambda"
  endpoint  = data.aws_lambda_function.alaram_function_name.arn
}
